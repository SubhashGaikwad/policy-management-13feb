import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RiderComponent } from './list/rider.component';
import { RiderDetailComponent } from './detail/rider-detail.component';
import { RiderUpdateComponent } from './update/rider-update.component';
import { RiderDeleteDialogComponent } from './delete/rider-delete-dialog.component';
import { RiderRoutingModule } from './route/rider-routing.module';

@NgModule({
  imports: [SharedModule, RiderRoutingModule],
  declarations: [RiderComponent, RiderDetailComponent, RiderUpdateComponent, RiderDeleteDialogComponent],
  entryComponents: [RiderDeleteDialogComponent],
})
export class RiderModule {}
