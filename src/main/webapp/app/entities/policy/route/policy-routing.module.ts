import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PolicyComponent } from '../list/policy.component';
import { PolicyDetailComponent } from '../detail/policy-detail.component';
import { PolicyUpdateComponent } from '../update/policy-update.component';
import { PolicyRoutingResolveService } from './policy-routing-resolve.service';

const policyRoute: Routes = [
  {
    path: '',
    component: PolicyComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PolicyDetailComponent,
    resolve: {
      policy: PolicyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PolicyUpdateComponent,
    resolve: {
      policy: PolicyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PolicyUpdateComponent,
    resolve: {
      policy: PolicyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(policyRoute)],
  exports: [RouterModule],
})
export class PolicyRoutingModule {}
