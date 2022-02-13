import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMember, Member } from '../member.model';
import { MemberService } from '../service/member.service';
import { IPolicy } from 'app/entities/policy/policy.model';
import { PolicyService } from 'app/entities/policy/service/policy.service';

@Component({
  selector: 'jhi-member-update',
  templateUrl: './member-update.component.html',
})
export class MemberUpdateComponent implements OnInit {
  isSaving = false;

  policiesSharedCollection: IPolicy[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    age: [],
    relation: [],
    contactNo: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    policy: [],
  });

  constructor(
    protected memberService: MemberService,
    protected policyService: PolicyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ member }) => {
      this.updateForm(member);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const member = this.createFromForm();
    if (member.id !== undefined) {
      this.subscribeToSaveResponse(this.memberService.update(member));
    } else {
      this.subscribeToSaveResponse(this.memberService.create(member));
    }
  }

  trackPolicyById(index: number, item: IPolicy): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMember>>): void {
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

  protected updateForm(member: IMember): void {
    this.editForm.patchValue({
      id: member.id,
      name: member.name,
      age: member.age,
      relation: member.relation,
      contactNo: member.contactNo,
      lastModified: member.lastModified,
      lastModifiedBy: member.lastModifiedBy,
      policy: member.policy,
    });

    this.policiesSharedCollection = this.policyService.addPolicyToCollectionIfMissing(this.policiesSharedCollection, member.policy);
  }

  protected loadRelationshipsOptions(): void {
    this.policyService
      .query()
      .pipe(map((res: HttpResponse<IPolicy[]>) => res.body ?? []))
      .pipe(map((policies: IPolicy[]) => this.policyService.addPolicyToCollectionIfMissing(policies, this.editForm.get('policy')!.value)))
      .subscribe((policies: IPolicy[]) => (this.policiesSharedCollection = policies));
  }

  protected createFromForm(): IMember {
    return {
      ...new Member(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      age: this.editForm.get(['age'])!.value,
      relation: this.editForm.get(['relation'])!.value,
      contactNo: this.editForm.get(['contactNo'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      policy: this.editForm.get(['policy'])!.value,
    };
  }
}
