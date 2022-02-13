import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUsersType, getUsersTypeIdentifier } from '../users-type.model';

export type EntityResponseType = HttpResponse<IUsersType>;
export type EntityArrayResponseType = HttpResponse<IUsersType[]>;

@Injectable({ providedIn: 'root' })
export class UsersTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/users-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(usersType: IUsersType): Observable<EntityResponseType> {
    return this.http.post<IUsersType>(this.resourceUrl, usersType, { observe: 'response' });
  }

  update(usersType: IUsersType): Observable<EntityResponseType> {
    return this.http.put<IUsersType>(`${this.resourceUrl}/${getUsersTypeIdentifier(usersType) as number}`, usersType, {
      observe: 'response',
    });
  }

  partialUpdate(usersType: IUsersType): Observable<EntityResponseType> {
    return this.http.patch<IUsersType>(`${this.resourceUrl}/${getUsersTypeIdentifier(usersType) as number}`, usersType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUsersType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUsersType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUsersTypeToCollectionIfMissing(
    usersTypeCollection: IUsersType[],
    ...usersTypesToCheck: (IUsersType | null | undefined)[]
  ): IUsersType[] {
    const usersTypes: IUsersType[] = usersTypesToCheck.filter(isPresent);
    if (usersTypes.length > 0) {
      const usersTypeCollectionIdentifiers = usersTypeCollection.map(usersTypeItem => getUsersTypeIdentifier(usersTypeItem)!);
      const usersTypesToAdd = usersTypes.filter(usersTypeItem => {
        const usersTypeIdentifier = getUsersTypeIdentifier(usersTypeItem);
        if (usersTypeIdentifier == null || usersTypeCollectionIdentifiers.includes(usersTypeIdentifier)) {
          return false;
        }
        usersTypeCollectionIdentifiers.push(usersTypeIdentifier);
        return true;
      });
      return [...usersTypesToAdd, ...usersTypeCollection];
    }
    return usersTypeCollection;
  }
}
