import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPolicy } from '../policy.model';
import { PolicyService } from '../service/policy.service';

@Component({
  templateUrl: './policy-delete-dialog.component.html',
})
export class PolicyDeleteDialogComponent {
  policy?: IPolicy;

  constructor(protected policyService: PolicyService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.policyService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
