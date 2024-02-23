// RecoilAtom.ts
import { atom } from 'recoil';

export type HeroType = {
	name: string;
	color: string;
	money: number;
};

export const heroState = atom<HeroType | null>({
	key: 'heroState',
	default: null,
});