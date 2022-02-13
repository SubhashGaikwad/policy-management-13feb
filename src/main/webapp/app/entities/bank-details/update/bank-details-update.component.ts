import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IBankDetails, BankDetails } from '../bank-details.model';
import { BankDetailsService } from '../service/bank-details.service';

@Component({
  selector: 'jhi-bank-details-update',
  templateUrl: './bank-details-update.component.html',
})
export class BankDetailsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    branch: [],
    branchCode: [],
    city: [],
    contactNo: [],
    ifcCode: [],
    account: [],
    accountType: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(protected bankDetailsService: BankDetailsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bankDetails }) => {
      this.updateForm(bankDetails);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bankDetails = this.createFromForm();
    if (bankDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.bankDetailsService.update(bankDetails));
    } else {
      this.subscribeToSaveResponse(this.bankDetailsService.create(bankDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBankDetails>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(bankDetails: IBankDetails): void {
    this.editForm.patchValue({
      id: bankDetails.id,
      name: bankDetails.name,
      branch: bankDetails.branch,
      branchCode: bankDetails.branchCode,
      city: bankDetails.city,
      contactNo: bankDetails.contactNo,
      ifcCode: bankDetails.ifcCode,
      account: bankDetails.account,
      accountType: bankDetails.accountType,
      lastModified: bankDetails.lastModified,
      lastModifiedBy: bankDetails.lastModifiedBy,
    });
  }

  protected createFromForm(): IBankDetails {
    return {
      ...new BankDetails(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      branch: this.editForm.get(['branch'])!.value,
      branchCode: this.editForm.get(['branchCode'])!.value,
      city: this.editForm.get(['city'])!.value,
      contactNo: this.editForm.get(['contactNo'])!.value,
      ifcCode: this.editForm.get(['ifcCode'])!.value,
      account: this.editForm.get(['account'])!.value,
      accountType: this.editForm.get(['accountType'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
