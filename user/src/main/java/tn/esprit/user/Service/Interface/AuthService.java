package tn.esprit.user.Service.Interface;

import tn.esprit.user.Dto.RegisterRequest;
import tn.esprit.user.Dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
}
