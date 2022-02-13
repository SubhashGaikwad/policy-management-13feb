import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPolicy } from '../policy.model';

@Component({
  selector: 'jhi-policy-detail',
  templateUrl: './policy-detail.component.html',
})
export class PolicyDetailComponent implements OnInit {
  policy: IPolicy | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ policy }) => {
      this.policy = policy;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
