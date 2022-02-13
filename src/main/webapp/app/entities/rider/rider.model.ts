export interface IRider {
  id?: number;
  name?: string | null;
  commDate?: string | null;
  sum?: string | null;
  term?: string | null;
  ppt?: string | null;
  premium?: number | null;
  lastModified?: string;
  lastModifiedBy?: string;
}

export class Rider implements IRider {
  constructor(
    public id?: number,
    public name?: string | null,
    public commDate?: string | null,
    public sum?: string | null,
    public term?: string | null,
    public ppt?: string | null,
    public premium?: number | null,
    public lastModified?: string,
    public lastModifiedBy?: string
  ) {}
}

export function getRiderIdentifier(rider: IRider): number | undefined {
  return rider.id;
}
