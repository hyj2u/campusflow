package com.cnco.campusflow.menu;

import com.cnco.campusflow.user.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    CartEntity findByAppUser(AppUserEntity appUser);

}
