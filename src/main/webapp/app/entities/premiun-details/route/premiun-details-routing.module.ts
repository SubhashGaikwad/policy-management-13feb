import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PremiunDetailsComponent } from '../list/premiun-details.component';
import { PremiunDetailsDetailComponent } from '../detail/premiun-details-detail.component';
import { PremiunDetailsUpdateComponent } from '../update/premiun-details-update.component';
import { PremiunDetailsRoutingResolveService } from './premiun-details-routing-resolve.service';

const premiunDetailsRoute: Routes = [
  {
    path: '',
    component: PremiunDetailsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PremiunDetailsDetailComponent,
    resolve: {
      premiunDetails: PremiunDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PremiunDetailsUpdateComponent,
    resolve: {
      premiunDetails: PremiunDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PremiunDetailsUpdateComponent,
    resolve: {
      premiunDetails: PremiunDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(premiunDetailsRoute)],
  exports: [RouterModule],
})
export class PremiunDetailsRoutingModule {}
