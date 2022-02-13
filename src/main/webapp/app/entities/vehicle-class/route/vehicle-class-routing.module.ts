import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VehicleClassComponent } from '../list/vehicle-class.component';
import { VehicleClassDetailComponent } from '../detail/vehicle-class-detail.component';
import { VehicleClassUpdateComponent } from '../update/vehicle-class-update.component';
import { VehicleClassRoutingResolveService } from './vehicle-class-routing-resolve.service';

const vehicleClassRoute: Routes = [
  {
    path: '',
    component: VehicleClassComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VehicleClassDetailComponent,
    resolve: {
      vehicleClass: VehicleClassRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VehicleClassUpdateComponent,
    resolve: {
      vehicleClass: VehicleClassRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VehicleClassUpdateComponent,
    resolve: {
      vehicleClass: VehicleClassRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vehicleClassRoute)],
  exports: [RouterModule],
})
export class VehicleClassRoutingModule {}
