import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INominee, getNomineeIdentifier } from '../nominee.model';

export type EntityResponseType = HttpResponse<INominee>;
export type EntityArrayResponseType = HttpResponse<INominee[]>;

@Injectable({ providedIn: 'root' })
export class NomineeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nominees');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(nominee: INominee): Observable<EntityResponseType> {
    return this.http.post<INominee>(this.resourceUrl, nominee, { observe: 'response' });
  }

  update(nominee: INominee): Observable<EntityResponseType> {
    return this.http.put<INominee>(`${this.resourceUrl}/${getNomineeIdentifier(nominee) as number}`, nominee, { observe: 'response' });
  }

  partialUpdate(nominee: INominee): Observable<EntityResponseType> {
    return this.http.patch<INominee>(`${this.resourceUrl}/${getNomineeIdentifier(nominee) as number}`, nominee, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INominee>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INominee[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNomineeToCollectionIfMissing(nomineeCollection: INominee[], ...nomineesToCheck: (INominee | null | undefined)[]): INominee[] {
    const nominees: INominee[] = nomineesToCheck.filter(isPresent);
    if (nominees.length > 0) {
      const nomineeCollectionIdentifiers = nomineeCollection.map(nomineeItem => getNomineeIdentifier(nomineeItem)!);
      const nomineesToAdd = nominees.filter(nomineeItem => {
        const nomineeIdentifier = getNomineeIdentifier(nomineeItem);
        if (nomineeIdentifier == null || nomineeCollectionIdentifiers.includes(nomineeIdentifier)) {
          return false;
        }
        nomineeCollectionIdentifiers.push(nomineeIdentifier);
        return true;
      });
      return [...nomineesToAdd, ...nomineeCollection];
    }
    return nomineeCollection;
  }
}
