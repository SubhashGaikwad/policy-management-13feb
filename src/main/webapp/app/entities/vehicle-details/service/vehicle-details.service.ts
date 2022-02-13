import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVehicleDetails, getVehicleDetailsIdentifier } from '../vehicle-details.model';

export type EntityResponseType = HttpResponse<IVehicleDetails>;
export type EntityArrayResponseType = HttpResponse<IVehicleDetails[]>;

@Injectable({ providedIn: 'root' })
export class VehicleDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vehicle-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(vehicleDetails: IVehicleDetails): Observable<EntityResponseType> {
    return this.http.post<IVehicleDetails>(this.resourceUrl, vehicleDetails, { observe: 'response' });
  }

  update(vehicleDetails: IVehicleDetails): Observable<EntityResponseType> {
    return this.http.put<IVehicleDetails>(`${this.resourceUrl}/${getVehicleDetailsIdentifier(vehicleDetails) as number}`, vehicleDetails, {
      observe: 'response',
    });
  }

  partialUpdate(vehicleDetails: IVehicleDetails): Observable<EntityResponseType> {
    return this.http.patch<IVehicleDetails>(
      `${this.resourceUrl}/${getVehicleDetailsIdentifier(vehicleDetails) as number}`,
      vehicleDetails,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVehicleDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVehicleDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVehicleDetailsToCollectionIfMissing(
    vehicleDetailsCollection: IVehicleDetails[],
    ...vehicleDetailsToCheck: (IVehicleDetails | null | undefined)[]
  ): IVehicleDetails[] {
    const vehicleDetails: IVehicleDetails[] = vehicleDetailsToCheck.filter(isPresent);
    if (vehicleDetails.length > 0) {
      const vehicleDetailsCollectionIdentifiers = vehicleDetailsCollection.map(
        vehicleDetailsItem => getVehicleDetailsIdentifier(vehicleDetailsItem)!
      );
      const vehicleDetailsToAdd = vehicleDetails.filter(vehicleDetailsItem => {
        const vehicleDetailsIdentifier = getVehicleDetailsIdentifier(vehicleDetailsItem);
        if (vehicleDetailsIdentifier == null || vehicleDetailsCollectionIdentifiers.includes(vehicleDetailsIdentifier)) {
          return false;
        }
        vehicleDetailsCollectionIdentifiers.push(vehicleDetailsIdentifier);
        return true;
      });
      return [...vehicleDetailsToAdd, ...vehicleDetailsCollection];
    }
    return vehicleDetailsCollection;
  }
}
