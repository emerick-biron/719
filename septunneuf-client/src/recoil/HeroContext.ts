// RecoilAtom.ts
import { atom } from 'recoil';

export type HeroType = {
	id: number;
	name: string;
	color: string;
};

export const heroState = atom<HeroType | null>({
	key: 'heroState',
	default: null,
});
