import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IParameterLookup, getParameterLookupIdentifier } from '../parameter-lookup.model';

export type EntityResponseType = HttpResponse<IParameterLookup>;
export type EntityArrayResponseType = HttpResponse<IParameterLookup[]>;

@Injectable({ providedIn: 'root' })
export class ParameterLookupService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/parameter-lookups');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(parameterLookup: IParameterLookup): Observable<EntityResponseType> {
    return this.http.post<IParameterLookup>(this.resourceUrl, parameterLookup, { observe: 'response' });
  }

  update(parameterLookup: IParameterLookup): Observable<EntityResponseType> {
    return this.http.put<IParameterLookup>(
      `${this.resourceUrl}/${getParameterLookupIdentifier(parameterLookup) as number}`,
      parameterLookup,
      { observe: 'response' }
    );
  }

  partialUpdate(parameterLookup: IParameterLookup): Observable<EntityResponseType> {
    return this.http.patch<IParameterLookup>(
      `${this.resourceUrl}/${getParameterLookupIdentifier(parameterLookup) as number}`,
      parameterLookup,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IParameterLookup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParameterLookup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addParameterLookupToCollectionIfMissing(
    parameterLookupCollection: IParameterLookup[],
    ...parameterLookupsToCheck: (IParameterLookup | null | undefined)[]
  ): IParameterLookup[] {
    const parameterLookups: IParameterLookup[] = parameterLookupsToCheck.filter(isPresent);
    if (parameterLookups.length > 0) {
      const parameterLookupCollectionIdentifiers = parameterLookupCollection.map(
        parameterLookupItem => getParameterLookupIdentifier(parameterLookupItem)!
      );
      const parameterLookupsToAdd = parameterLookups.filter(parameterLookupItem => {
        const parameterLookupIdentifier = getParameterLookupIdentifier(parameterLookupItem);
        if (parameterLookupIdentifier == null || parameterLookupCollectionIdentifiers.includes(parameterLookupIdentifier)) {
          return false;
        }
        parameterLookupCollectionIdentifiers.push(parameterLookupIdentifier);
        return true;
      });
      return [...parameterLookupsToAdd, ...parameterLookupCollection];
    }
    return parameterLookupCollection;
  }
}
