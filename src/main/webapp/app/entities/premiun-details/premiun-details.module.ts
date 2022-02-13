import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PremiunDetailsComponent } from './list/premiun-details.component';
import { PremiunDetailsDetailComponent } from './detail/premiun-details-detail.component';
import { PremiunDetailsUpdateComponent } from './update/premiun-details-update.component';
import { PremiunDetailsDeleteDialogComponent } from './delete/premiun-details-delete-dialog.component';
import { PremiunDetailsRoutingModule } from './route/premiun-details-routing.module';

@NgModule({
  imports: [SharedModule, PremiunDetailsRoutingModule],
  declarations: [
    PremiunDetailsComponent,
    PremiunDetailsDetailComponent,
    PremiunDetailsUpdateComponent,
    PremiunDetailsDeleteDialogComponent,
  ],
  entryComponents: [PremiunDetailsDeleteDialogComponent],
})
export class PremiunDetailsModule {}
