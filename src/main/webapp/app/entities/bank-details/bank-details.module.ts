import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BankDetailsComponent } from './list/bank-details.component';
import { BankDetailsDetailComponent } from './detail/bank-details-detail.component';
import { BankDetailsUpdateComponent } from './update/bank-details-update.component';
import { BankDetailsDeleteDialogComponent } from './delete/bank-details-delete-dialog.component';
import { BankDetailsRoutingModule } from './route/bank-details-routing.module';

@NgModule({
  imports: [SharedModule, BankDetailsRoutingModule],
  declarations: [BankDetailsComponent, BankDetailsDetailComponent, BankDetailsUpdateComponent, BankDetailsDeleteDialogComponent],
  entryComponents: [BankDetailsDeleteDialogComponent],
})
export class BankDetailsModule {}
