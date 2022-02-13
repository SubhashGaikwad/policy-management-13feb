import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UsersTypeComponent } from '../list/users-type.component';
import { UsersTypeDetailComponent } from '../detail/users-type-detail.component';
import { UsersTypeUpdateComponent } from '../update/users-type-update.component';
import { UsersTypeRoutingResolveService } from './users-type-routing-resolve.service';

const usersTypeRoute: Routes = [
  {
    path: '',
    component: UsersTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UsersTypeDetailComponent,
    resolve: {
      usersType: UsersTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UsersTypeUpdateComponent,
    resolve: {
      usersType: UsersTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UsersTypeUpdateComponent,
    resolve: {
      usersType: UsersTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(usersTypeRoute)],
  exports: [RouterModule],
})
export class UsersTypeRoutingModule {}
