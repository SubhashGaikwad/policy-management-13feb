import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRider, Rider } from '../rider.model';
import { RiderService } from '../service/rider.service';

@Injectable({ providedIn: 'root' })
export class RiderRoutingResolveService implements Resolve<IRider> {
  constructor(protected service: RiderService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRider> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((rider: HttpResponse<Rider>) => {
          if (rider.body) {
            return of(rider.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Rider());
  }
}
