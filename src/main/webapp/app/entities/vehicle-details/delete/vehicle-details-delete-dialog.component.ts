import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehicleDetails } from '../vehicle-details.model';
import { VehicleDetailsService } from '../service/vehicle-details.service';

@Component({
  templateUrl: './vehicle-details-delete-dialog.component.html',
})
export class VehicleDetailsDeleteDialogComponent {
  vehicleDetails?: IVehicleDetails;

  constructor(protected vehicleDetailsService: VehicleDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehicleDetailsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
