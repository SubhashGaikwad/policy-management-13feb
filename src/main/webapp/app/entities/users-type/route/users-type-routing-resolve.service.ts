import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUsersType, UsersType } from '../users-type.model';
import { UsersTypeService } from '../service/users-type.service';

@Injectable({ providedIn: 'root' })
export class UsersTypeRoutingResolveService implements Resolve<IUsersType> {
  constructor(protected service: UsersTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUsersType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((usersType: HttpResponse<UsersType>) => {
          if (usersType.body) {
            return of(usersType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UsersType());
  }
}
