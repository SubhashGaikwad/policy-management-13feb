import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BankDetailsComponent } from '../list/bank-details.component';
import { BankDetailsDetailComponent } from '../detail/bank-details-detail.component';
import { BankDetailsUpdateComponent } from '../update/bank-details-update.component';
import { BankDetailsRoutingResolveService } from './bank-details-routing-resolve.service';

const bankDetailsRoute: Routes = [
  {
    path: '',
    component: BankDetailsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BankDetailsDetailComponent,
    resolve: {
      bankDetails: BankDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BankDetailsUpdateComponent,
    resolve: {
      bankDetails: BankDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BankDetailsUpdateComponent,
    resolve: {
      bankDetails: BankDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bankDetailsRoute)],
  exports: [RouterModule],
})
export class BankDetailsRoutingModule {}
