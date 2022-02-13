import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VehicleClassComponent } from './list/vehicle-class.component';
import { VehicleClassDetailComponent } from './detail/vehicle-class-detail.component';
import { VehicleClassUpdateComponent } from './update/vehicle-class-update.component';
import { VehicleClassDeleteDialogComponent } from './delete/vehicle-class-delete-dialog.component';
import { VehicleClassRoutingModule } from './route/vehicle-class-routing.module';

@NgModule({
  imports: [SharedModule, VehicleClassRoutingModule],
  declarations: [VehicleClassComponent, VehicleClassDetailComponent, VehicleClassUpdateComponent, VehicleClassDeleteDialogComponent],
  entryComponents: [VehicleClassDeleteDialogComponent],
})
export class VehicleClassModule {}
