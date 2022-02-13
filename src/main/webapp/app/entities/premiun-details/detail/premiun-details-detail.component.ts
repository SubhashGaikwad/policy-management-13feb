import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPremiunDetails } from '../premiun-details.model';

@Component({
  selector: 'jhi-premiun-details-detail',
  templateUrl: './premiun-details-detail.component.html',
})
export class PremiunDetailsDetailComponent implements OnInit {
  premiunDetails: IPremiunDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ premiunDetails }) => {
      this.premiunDetails = premiunDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
