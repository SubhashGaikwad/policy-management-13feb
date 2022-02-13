import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPremiunDetails, getPremiunDetailsIdentifier } from '../premiun-details.model';

export type EntityResponseType = HttpResponse<IPremiunDetails>;
export type EntityArrayResponseType = HttpResponse<IPremiunDetails[]>;

@Injectable({ providedIn: 'root' })
export class PremiunDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/premiun-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(premiunDetails: IPremiunDetails): Observable<EntityResponseType> {
    return this.http.post<IPremiunDetails>(this.resourceUrl, premiunDetails, { observe: 'response' });
  }

  update(premiunDetails: IPremiunDetails): Observable<EntityResponseType> {
    return this.http.put<IPremiunDetails>(`${this.resourceUrl}/${getPremiunDetailsIdentifier(premiunDetails) as number}`, premiunDetails, {
      observe: 'response',
    });
  }

  partialUpdate(premiunDetails: IPremiunDetails): Observable<EntityResponseType> {
    return this.http.patch<IPremiunDetails>(
      `${this.resourceUrl}/${getPremiunDetailsIdentifier(premiunDetails) as number}`,
      premiunDetails,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPremiunDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPremiunDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPremiunDetailsToCollectionIfMissing(
    premiunDetailsCollection: IPremiunDetails[],
    ...premiunDetailsToCheck: (IPremiunDetails | null | undefined)[]
  ): IPremiunDetails[] {
    const premiunDetails: IPremiunDetails[] = premiunDetailsToCheck.filter(isPresent);
    if (premiunDetails.length > 0) {
      const premiunDetailsCollectionIdentifiers = premiunDetailsCollection.map(
        premiunDetailsItem => getPremiunDetailsIdentifier(premiunDetailsItem)!
      );
      const premiunDetailsToAdd = premiunDetails.filter(premiunDetailsItem => {
        const premiunDetailsIdentifier = getPremiunDetailsIdentifier(premiunDetailsItem);
        if (premiunDetailsIdentifier == null || premiunDetailsCollectionIdentifiers.includes(premiunDetailsIdentifier)) {
          return false;
        }
        premiunDetailsCollectionIdentifiers.push(premiunDetailsIdentifier);
        return true;
      });
      return [...premiunDetailsToAdd, ...premiunDetailsCollection];
    }
    return premiunDetailsCollection;
  }
}
