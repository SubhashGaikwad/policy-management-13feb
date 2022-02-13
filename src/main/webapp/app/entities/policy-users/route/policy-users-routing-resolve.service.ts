import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPolicyUsers, PolicyUsers } from '../policy-users.model';
import { PolicyUsersService } from '../service/policy-users.service';

@Injectable({ providedIn: 'root' })
export class PolicyUsersRoutingResolveService implements Resolve<IPolicyUsers> {
  constructor(protected service: PolicyUsersService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPolicyUsers> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((policyUsers: HttpResponse<PolicyUsers>) => {
          if (policyUsers.body) {
            return of(policyUsers.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PolicyUsers());
  }
}
