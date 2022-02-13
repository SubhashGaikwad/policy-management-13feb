import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRider } from '../rider.model';
import { RiderService } from '../service/rider.service';

@Component({
  templateUrl: './rider-delete-dialog.component.html',
})
export class RiderDeleteDialogComponent {
  rider?: IRider;

  constructor(protected riderService: RiderService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.riderService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
