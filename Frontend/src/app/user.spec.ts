import { User } from './user';

describe('User', () => {
  it('should create an instance', () => expect(new User(1, "Ankit", "ankit@gmail.com", { id: 1, name: "Team1" })).toBeTruthy());
});
