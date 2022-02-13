import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { INominee, Nominee } from '../nominee.model';
import { NomineeService } from '../service/nominee.service';
import { IPolicy } from 'app/entities/policy/policy.model';
import { PolicyService } from 'app/entities/policy/service/policy.service';

@Component({
  selector: 'jhi-nominee-update',
  templateUrl: './nominee-update.component.html',
})
export class NomineeUpdateComponent implements OnInit {
  isSaving = false;

  policiesSharedCollection: IPolicy[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    relation: [],
    nomineePercentage: [],
    contactNo: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    policy: [],
  });

  constructor(
    protected nomineeService: NomineeService,
    protected policyService: PolicyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nominee }) => {
      this.updateForm(nominee);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nominee = this.createFromForm();
    if (nominee.id !== undefined) {
      this.subscribeToSaveResponse(this.nomineeService.update(nominee));
    } else {
      this.subscribeToSaveResponse(this.nomineeService.create(nominee));
    }
  }

  trackPolicyById(index: number, item: IPolicy): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INominee>>): void {
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

  protected updateForm(nominee: INominee): void {
    this.editForm.patchValue({
      id: nominee.id,
      name: nominee.name,
      relation: nominee.relation,
      nomineePercentage: nominee.nomineePercentage,
      contactNo: nominee.contactNo,
      lastModified: nominee.lastModified,
      lastModifiedBy: nominee.lastModifiedBy,
      policy: nominee.policy,
    });

    this.policiesSharedCollection = this.policyService.addPolicyToCollectionIfMissing(this.policiesSharedCollection, nominee.policy);
  }

  protected loadRelationshipsOptions(): void {
    this.policyService
      .query()
      .pipe(map((res: HttpResponse<IPolicy[]>) => res.body ?? []))
      .pipe(map((policies: IPolicy[]) => this.policyService.addPolicyToCollectionIfMissing(policies, this.editForm.get('policy')!.value)))
      .subscribe((policies: IPolicy[]) => (this.policiesSharedCollection = policies));
  }

  protected createFromForm(): INominee {
    return {
      ...new Nominee(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      relation: this.editForm.get(['relation'])!.value,
      nomineePercentage: this.editForm.get(['nomineePercentage'])!.value,
      contactNo: this.editForm.get(['contactNo'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      policy: this.editForm.get(['policy'])!.value,
    };
  }
}
