package com.SpaceX.Reggie.Service.Impl;

import com.SpaceX.Reggie.Entity.AddressBook;
import com.SpaceX.Reggie.Mapper.AddressBookMapper;
import com.SpaceX.Reggie.Service.AddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
