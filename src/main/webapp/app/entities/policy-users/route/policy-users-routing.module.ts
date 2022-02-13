import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PolicyUsersComponent } from '../list/policy-users.component';
import { PolicyUsersDetailComponent } from '../detail/policy-users-detail.component';
import { PolicyUsersUpdateComponent } from '../update/policy-users-update.component';
import { PolicyUsersRoutingResolveService } from './policy-users-routing-resolve.service';

const policyUsersRoute: Routes = [
  {
    path: '',
    component: PolicyUsersComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PolicyUsersDetailComponent,
    resolve: {
      policyUsers: PolicyUsersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PolicyUsersUpdateComponent,
    resolve: {
      policyUsers: PolicyUsersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PolicyUsersUpdateComponent,
    resolve: {
      policyUsers: PolicyUsersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(policyUsersRoute)],
  exports: [RouterModule],
})
export class PolicyUsersRoutingModule {}
