import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRider, getRiderIdentifier } from '../rider.model';

export type EntityResponseType = HttpResponse<IRider>;
export type EntityArrayResponseType = HttpResponse<IRider[]>;

@Injectable({ providedIn: 'root' })
export class RiderService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/riders');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(rider: IRider): Observable<EntityResponseType> {
    return this.http.post<IRider>(this.resourceUrl, rider, { observe: 'response' });
  }

  update(rider: IRider): Observable<EntityResponseType> {
    return this.http.put<IRider>(`${this.resourceUrl}/${getRiderIdentifier(rider) as number}`, rider, { observe: 'response' });
  }

  partialUpdate(rider: IRider): Observable<EntityResponseType> {
    return this.http.patch<IRider>(`${this.resourceUrl}/${getRiderIdentifier(rider) as number}`, rider, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRider>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRider[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRiderToCollectionIfMissing(riderCollection: IRider[], ...ridersToCheck: (IRider | null | undefined)[]): IRider[] {
    const riders: IRider[] = ridersToCheck.filter(isPresent);
    if (riders.length > 0) {
      const riderCollectionIdentifiers = riderCollection.map(riderItem => getRiderIdentifier(riderItem)!);
      const ridersToAdd = riders.filter(riderItem => {
        const riderIdentifier = getRiderIdentifier(riderItem);
        if (riderIdentifier == null || riderCollectionIdentifiers.includes(riderIdentifier)) {
          return false;
        }
        riderCollectionIdentifiers.push(riderIdentifier);
        return true;
      });
      return [...ridersToAdd, ...riderCollection];
    }
    return riderCollection;
  }
}
