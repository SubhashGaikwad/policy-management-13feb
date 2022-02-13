import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehicleClass } from '../vehicle-class.model';
import { VehicleClassService } from '../service/vehicle-class.service';

@Component({
  templateUrl: './vehicle-class-delete-dialog.component.html',
})
export class VehicleClassDeleteDialogComponent {
  vehicleClass?: IVehicleClass;

  constructor(protected vehicleClassService: VehicleClassService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehicleClassService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
