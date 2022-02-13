import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RiderComponent } from '../list/rider.component';
import { RiderDetailComponent } from '../detail/rider-detail.component';
import { RiderUpdateComponent } from '../update/rider-update.component';
import { RiderRoutingResolveService } from './rider-routing-resolve.service';

const riderRoute: Routes = [
  {
    path: '',
    component: RiderComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RiderDetailComponent,
    resolve: {
      rider: RiderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RiderUpdateComponent,
    resolve: {
      rider: RiderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RiderUpdateComponent,
    resolve: {
      rider: RiderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(riderRoute)],
  exports: [RouterModule],
})
export class RiderRoutingModule {}
