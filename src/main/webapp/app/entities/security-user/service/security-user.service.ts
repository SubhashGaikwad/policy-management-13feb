import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISecurityUser, getSecurityUserIdentifier } from '../security-user.model';

export type EntityResponseType = HttpResponse<ISecurityUser>;
export type EntityArrayResponseType = HttpResponse<ISecurityUser[]>;

@Injectable({ providedIn: 'root' })
export class SecurityUserService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/security-users');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(securityUser: ISecurityUser): Observable<EntityResponseType> {
    return this.http.post<ISecurityUser>(this.resourceUrl, securityUser, { observe: 'response' });
  }

  update(securityUser: ISecurityUser): Observable<EntityResponseType> {
    return this.http.put<ISecurityUser>(`${this.resourceUrl}/${getSecurityUserIdentifier(securityUser) as number}`, securityUser, {
      observe: 'response',
    });
  }

  partialUpdate(securityUser: ISecurityUser): Observable<EntityResponseType> {
    return this.http.patch<ISecurityUser>(`${this.resourceUrl}/${getSecurityUserIdentifier(securityUser) as number}`, securityUser, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISecurityUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISecurityUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSecurityUserToCollectionIfMissing(
    securityUserCollection: ISecurityUser[],
    ...securityUsersToCheck: (ISecurityUser | null | undefined)[]
  ): ISecurityUser[] {
    const securityUsers: ISecurityUser[] = securityUsersToCheck.filter(isPresent);
    if (securityUsers.length > 0) {
      const securityUserCollectionIdentifiers = securityUserCollection.map(
        securityUserItem => getSecurityUserIdentifier(securityUserItem)!
      );
      const securityUsersToAdd = securityUsers.filter(securityUserItem => {
        const securityUserIdentifier = getSecurityUserIdentifier(securityUserItem);
        if (securityUserIdentifier == null || securityUserCollectionIdentifiers.includes(securityUserIdentifier)) {
          return false;
        }
        securityUserCollectionIdentifiers.push(securityUserIdentifier);
        return true;
      });
      return [...securityUsersToAdd, ...securityUserCollection];
    }
    return securityUserCollection;
  }
}
