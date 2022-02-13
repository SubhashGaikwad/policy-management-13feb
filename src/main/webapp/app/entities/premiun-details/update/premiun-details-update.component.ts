import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPremiunDetails, PremiunDetails } from '../premiun-details.model';
import { PremiunDetailsService } from '../service/premiun-details.service';

@Component({
  selector: 'jhi-premiun-details-update',
  templateUrl: './premiun-details-update.component.html',
})
export class PremiunDetailsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    premium: [],
    otherLoading: [],
    otherDiscount: [],
    addOnPremium: [],
    liabilityPremium: [],
    odPremium: [],
    personalAccidentDiscount: [],
    personalAccident: [],
    grossPremium: [],
    gst: [],
    netPremium: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(
    protected premiunDetailsService: PremiunDetailsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ premiunDetails }) => {
      this.updateForm(premiunDetails);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const premiunDetails = this.createFromForm();
    if (premiunDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.premiunDetailsService.update(premiunDetails));
    } else {
      this.subscribeToSaveResponse(this.premiunDetailsService.create(premiunDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPremiunDetails>>): void {
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

  protected updateForm(premiunDetails: IPremiunDetails): void {
    this.editForm.patchValue({
      id: premiunDetails.id,
      premium: premiunDetails.premium,
      otherLoading: premiunDetails.otherLoading,
      otherDiscount: premiunDetails.otherDiscount,
      addOnPremium: premiunDetails.addOnPremium,
      liabilityPremium: premiunDetails.liabilityPremium,
      odPremium: premiunDetails.odPremium,
      personalAccidentDiscount: premiunDetails.personalAccidentDiscount,
      personalAccident: premiunDetails.personalAccident,
      grossPremium: premiunDetails.grossPremium,
      gst: premiunDetails.gst,
      netPremium: premiunDetails.netPremium,
      lastModified: premiunDetails.lastModified,
      lastModifiedBy: premiunDetails.lastModifiedBy,
    });
  }

  protected createFromForm(): IPremiunDetails {
    return {
      ...new PremiunDetails(),
      id: this.editForm.get(['id'])!.value,
      premium: this.editForm.get(['premium'])!.value,
      otherLoading: this.editForm.get(['otherLoading'])!.value,
      otherDiscount: this.editForm.get(['otherDiscount'])!.value,
      addOnPremium: this.editForm.get(['addOnPremium'])!.value,
      liabilityPremium: this.editForm.get(['liabilityPremium'])!.value,
      odPremium: this.editForm.get(['odPremium'])!.value,
      personalAccidentDiscount: this.editForm.get(['personalAccidentDiscount'])!.value,
      personalAccident: this.editForm.get(['personalAccident'])!.value,
      grossPremium: this.editForm.get(['grossPremium'])!.value,
      gst: this.editForm.get(['gst'])!.value,
      netPremium: this.editForm.get(['netPremium'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }
}
