import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUsersType } from '../users-type.model';
import { UsersTypeService } from '../service/users-type.service';

@Component({
  templateUrl: './users-type-delete-dialog.component.html',
})
export class UsersTypeDeleteDialogComponent {
  usersType?: IUsersType;

  constructor(protected usersTypeService: UsersTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.usersTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
