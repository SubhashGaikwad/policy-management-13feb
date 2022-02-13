import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVehicleDetails, VehicleDetails } from '../vehicle-details.model';
import { VehicleDetailsService } from '../service/vehicle-details.service';

@Injectable({ providedIn: 'root' })
export class VehicleDetailsRoutingResolveService implements Resolve<IVehicleDetails> {
  constructor(protected service: VehicleDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehicleDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vehicleDetails: HttpResponse<VehicleDetails>) => {
          if (vehicleDetails.body) {
            return of(vehicleDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VehicleDetails());
  }
}
