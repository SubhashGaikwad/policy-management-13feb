import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVehicleClass, getVehicleClassIdentifier } from '../vehicle-class.model';

export type EntityResponseType = HttpResponse<IVehicleClass>;
export type EntityArrayResponseType = HttpResponse<IVehicleClass[]>;

@Injectable({ providedIn: 'root' })
export class VehicleClassService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vehicle-classes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(vehicleClass: IVehicleClass): Observable<EntityResponseType> {
    return this.http.post<IVehicleClass>(this.resourceUrl, vehicleClass, { observe: 'response' });
  }

  update(vehicleClass: IVehicleClass): Observable<EntityResponseType> {
    return this.http.put<IVehicleClass>(`${this.resourceUrl}/${getVehicleClassIdentifier(vehicleClass) as number}`, vehicleClass, {
      observe: 'response',
    });
  }

  partialUpdate(vehicleClass: IVehicleClass): Observable<EntityResponseType> {
    return this.http.patch<IVehicleClass>(`${this.resourceUrl}/${getVehicleClassIdentifier(vehicleClass) as number}`, vehicleClass, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVehicleClass>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVehicleClass[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVehicleClassToCollectionIfMissing(
    vehicleClassCollection: IVehicleClass[],
    ...vehicleClassesToCheck: (IVehicleClass | null | undefined)[]
  ): IVehicleClass[] {
    const vehicleClasses: IVehicleClass[] = vehicleClassesToCheck.filter(isPresent);
    if (vehicleClasses.length > 0) {
      const vehicleClassCollectionIdentifiers = vehicleClassCollection.map(
        vehicleClassItem => getVehicleClassIdentifier(vehicleClassItem)!
      );
      const vehicleClassesToAdd = vehicleClasses.filter(vehicleClassItem => {
        const vehicleClassIdentifier = getVehicleClassIdentifier(vehicleClassItem);
        if (vehicleClassIdentifier == null || vehicleClassCollectionIdentifiers.includes(vehicleClassIdentifier)) {
          return false;
        }
        vehicleClassCollectionIdentifiers.push(vehicleClassIdentifier);
        return true;
      });
      return [...vehicleClassesToAdd, ...vehicleClassCollection];
    }
    return vehicleClassCollection;
  }
}
