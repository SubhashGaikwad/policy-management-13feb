import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBankDetails, BankDetails } from '../bank-details.model';
import { BankDetailsService } from '../service/bank-details.service';

@Injectable({ providedIn: 'root' })
export class BankDetailsRoutingResolveService implements Resolve<IBankDetails> {
  constructor(protected service: BankDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBankDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bankDetails: HttpResponse<BankDetails>) => {
          if (bankDetails.body) {
            return of(bankDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BankDetails());
  }
}
