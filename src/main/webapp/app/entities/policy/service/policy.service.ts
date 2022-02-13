import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPolicy, getPolicyIdentifier } from '../policy.model';

export type EntityResponseType = HttpResponse<IPolicy>;
export type EntityArrayResponseType = HttpResponse<IPolicy[]>;

@Injectable({ providedIn: 'root' })
export class PolicyService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/policies');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(policy: IPolicy): Observable<EntityResponseType> {
    return this.http.post<IPolicy>(this.resourceUrl, policy, { observe: 'response' });
  }

  update(policy: IPolicy): Observable<EntityResponseType> {
    return this.http.put<IPolicy>(`${this.resourceUrl}/${getPolicyIdentifier(policy) as number}`, policy, { observe: 'response' });
  }

  partialUpdate(policy: IPolicy): Observable<EntityResponseType> {
    return this.http.patch<IPolicy>(`${this.resourceUrl}/${getPolicyIdentifier(policy) as number}`, policy, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPolicy>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPolicy[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPolicyToCollectionIfMissing(policyCollection: IPolicy[], ...policiesToCheck: (IPolicy | null | undefined)[]): IPolicy[] {
    const policies: IPolicy[] = policiesToCheck.filter(isPresent);
    if (policies.length > 0) {
      const policyCollectionIdentifiers = policyCollection.map(policyItem => getPolicyIdentifier(policyItem)!);
      const policiesToAdd = policies.filter(policyItem => {
        const policyIdentifier = getPolicyIdentifier(policyItem);
        if (policyIdentifier == null || policyCollectionIdentifiers.includes(policyIdentifier)) {
          return false;
        }
        policyCollectionIdentifiers.push(policyIdentifier);
        return true;
      });
      return [...policiesToAdd, ...policyCollection];
    }
    return policyCollection;
  }
}
