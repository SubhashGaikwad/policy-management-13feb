import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPremiunDetails } from '../premiun-details.model';
import { PremiunDetailsService } from '../service/premiun-details.service';

@Component({
  templateUrl: './premiun-details-delete-dialog.component.html',
})
export class PremiunDetailsDeleteDialogComponent {
  premiunDetails?: IPremiunDetails;

  constructor(protected premiunDetailsService: PremiunDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.premiunDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
