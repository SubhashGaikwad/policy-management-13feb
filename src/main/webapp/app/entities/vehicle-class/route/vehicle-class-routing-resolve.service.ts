import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVehicleClass, VehicleClass } from '../vehicle-class.model';
import { VehicleClassService } from '../service/vehicle-class.service';

@Injectable({ providedIn: 'root' })
export class VehicleClassRoutingResolveService implements Resolve<IVehicleClass> {
  constructor(protected service: VehicleClassService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehicleClass> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vehicleClass: HttpResponse<VehicleClass>) => {
          if (vehicleClass.body) {
            return of(vehicleClass.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VehicleClass());
  }
}
