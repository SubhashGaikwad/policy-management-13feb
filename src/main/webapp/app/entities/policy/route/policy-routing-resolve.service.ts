import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPolicy, Policy } from '../policy.model';
import { PolicyService } from '../service/policy.service';

@Injectable({ providedIn: 'root' })
export class PolicyRoutingResolveService implements Resolve<IPolicy> {
  constructor(protected service: PolicyService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPolicy> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((policy: HttpResponse<Policy>) => {
          if (policy.body) {
            return of(policy.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Policy());
  }
}
