import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBankDetails } from '../bank-details.model';
import { BankDetailsService } from '../service/bank-details.service';

@Component({
  templateUrl: './bank-details-delete-dialog.component.html',
})
export class BankDetailsDeleteDialogComponent {
  bankDetails?: IBankDetails;

  constructor(protected bankDetailsService: BankDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bankDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
