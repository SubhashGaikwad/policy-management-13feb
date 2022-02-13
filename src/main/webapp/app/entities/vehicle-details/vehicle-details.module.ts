import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VehicleDetailsComponent } from './list/vehicle-details.component';
import { VehicleDetailsDetailComponent } from './detail/vehicle-details-detail.component';
import { VehicleDetailsUpdateComponent } from './update/vehicle-details-update.component';
import { VehicleDetailsDeleteDialogComponent } from './delete/vehicle-details-delete-dialog.component';
import { VehicleDetailsRoutingModule } from './route/vehicle-details-routing.module';

@NgModule({
  imports: [SharedModule, VehicleDetailsRoutingModule],
  declarations: [
    VehicleDetailsComponent,
    VehicleDetailsDetailComponent,
    VehicleDetailsUpdateComponent,
    VehicleDetailsDeleteDialogComponent,
  ],
  entryComponents: [VehicleDetailsDeleteDialogComponent],
})
export class VehicleDetailsModule {}
