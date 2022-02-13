import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPolicyUsers, getPolicyUsersIdentifier } from '../policy-users.model';

export type EntityResponseType = HttpResponse<IPolicyUsers>;
export type EntityArrayResponseType = HttpResponse<IPolicyUsers[]>;

@Injectable({ providedIn: 'root' })
export class PolicyUsersService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/policy-users');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(policyUsers: IPolicyUsers): Observable<EntityResponseType> {
    return this.http.post<IPolicyUsers>(this.resourceUrl, policyUsers, { observe: 'response' });
  }

  update(policyUsers: IPolicyUsers): Observable<EntityResponseType> {
    return this.http.put<IPolicyUsers>(`${this.resourceUrl}/${getPolicyUsersIdentifier(policyUsers) as number}`, policyUsers, {
      observe: 'response',
    });
  }

  partialUpdate(policyUsers: IPolicyUsers): Observable<EntityResponseType> {
    return this.http.patch<IPolicyUsers>(`${this.resourceUrl}/${getPolicyUsersIdentifier(policyUsers) as number}`, policyUsers, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPolicyUsers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPolicyUsers[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPolicyUsersToCollectionIfMissing(
    policyUsersCollection: IPolicyUsers[],
    ...policyUsersToCheck: (IPolicyUsers | null | undefined)[]
  ): IPolicyUsers[] {
    const policyUsers: IPolicyUsers[] = policyUsersToCheck.filter(isPresent);
    if (policyUsers.length > 0) {
      const policyUsersCollectionIdentifiers = policyUsersCollection.map(policyUsersItem => getPolicyUsersIdentifier(policyUsersItem)!);
      const policyUsersToAdd = policyUsers.filter(policyUsersItem => {
        const policyUsersIdentifier = getPolicyUsersIdentifier(policyUsersItem);
        if (policyUsersIdentifier == null || policyUsersCollectionIdentifiers.includes(policyUsersIdentifier)) {
          return false;
        }
        policyUsersCollectionIdentifiers.push(policyUsersIdentifier);
        return true;
      });
      return [...policyUsersToAdd, ...policyUsersCollection];
    }
    return policyUsersCollection;
  }
}
