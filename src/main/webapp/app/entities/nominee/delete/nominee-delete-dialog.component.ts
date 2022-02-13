import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INominee } from '../nominee.model';
import { NomineeService } from '../service/nominee.service';

@Component({
  templateUrl: './nominee-delete-dialog.component.html',
})
export class NomineeDeleteDialogComponent {
  nominee?: INominee;

  constructor(protected nomineeService: NomineeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nomineeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
