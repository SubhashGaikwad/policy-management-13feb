import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBankDetails, getBankDetailsIdentifier } from '../bank-details.model';

export type EntityResponseType = HttpResponse<IBankDetails>;
export type EntityArrayResponseType = HttpResponse<IBankDetails[]>;

@Injectable({ providedIn: 'root' })
export class BankDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bank-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bankDetails: IBankDetails): Observable<EntityResponseType> {
    return this.http.post<IBankDetails>(this.resourceUrl, bankDetails, { observe: 'response' });
  }

  update(bankDetails: IBankDetails): Observable<EntityResponseType> {
    return this.http.put<IBankDetails>(`${this.resourceUrl}/${getBankDetailsIdentifier(bankDetails) as number}`, bankDetails, {
      observe: 'response',
    });
  }

  partialUpdate(bankDetails: IBankDetails): Observable<EntityResponseType> {
    return this.http.patch<IBankDetails>(`${this.resourceUrl}/${getBankDetailsIdentifier(bankDetails) as number}`, bankDetails, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBankDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBankDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBankDetailsToCollectionIfMissing(
    bankDetailsCollection: IBankDetails[],
    ...bankDetailsToCheck: (IBankDetails | null | undefined)[]
  ): IBankDetails[] {
    const bankDetails: IBankDetails[] = bankDetailsToCheck.filter(isPresent);
    if (bankDetails.length > 0) {
      const bankDetailsCollectionIdentifiers = bankDetailsCollection.map(bankDetailsItem => getBankDetailsIdentifier(bankDetailsItem)!);
      const bankDetailsToAdd = bankDetails.filter(bankDetailsItem => {
        const bankDetailsIdentifier = getBankDetailsIdentifier(bankDetailsItem);
        if (bankDetailsIdentifier == null || bankDetailsCollectionIdentifiers.includes(bankDetailsIdentifier)) {
          return false;
        }
        bankDetailsCollectionIdentifiers.push(bankDetailsIdentifier);
        return true;
      });
      return [...bankDetailsToAdd, ...bankDetailsCollection];
    }
    return bankDetailsCollection;
  }
}
