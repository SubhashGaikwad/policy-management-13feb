import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VehicleDetailsComponent } from '../list/vehicle-details.component';
import { VehicleDetailsDetailComponent } from '../detail/vehicle-details-detail.component';
import { VehicleDetailsUpdateComponent } from '../update/vehicle-details-update.component';
import { VehicleDetailsRoutingResolveService } from './vehicle-details-routing-resolve.service';

const vehicleDetailsRoute: Routes = [
  {
    path: '',
    component: VehicleDetailsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VehicleDetailsDetailComponent,
    resolve: {
      vehicleDetails: VehicleDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VehicleDetailsUpdateComponent,
    resolve: {
      vehicleDetails: VehicleDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VehicleDetailsUpdateComponent,
    resolve: {
      vehicleDetails: VehicleDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vehicleDetailsRoute)],
  exports: [RouterModule],
})
export class VehicleDetailsRoutingModule {}
