import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPolicyUsers } from '../policy-users.model';
import { PolicyUsersService } from '../service/policy-users.service';

@Component({
  templateUrl: './policy-users-delete-dialog.component.html',
})
export class PolicyUsersDeleteDialogComponent {
  policyUsers?: IPolicyUsers;

  constructor(protected policyUsersService: PolicyUsersService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.policyUsersService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
