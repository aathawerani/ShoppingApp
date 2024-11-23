import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class LoginBloc implements StateStreamableSource {
  bool login = false;
  bool _isClosed = false;
  @override
  bool get isClosed => _isClosed;
  @override
  close() {}
  late State _state;
  @override
  State get state => _state;
  late Stream _stream;
  @override
  Stream get stream => _stream;
}

class LoginBlocProvider extends InheritedWidget {
  final LoginBloc loginBloc;
  LoginBlocProvider({
    Key? key,
    required this.loginBloc,
    Widget? child,
  }) : super(key: key, child: child!);
  @override
  bool updateShouldNotify(InheritedWidget oldWidget) => true;
  static LoginBloc of(BuildContext context) =>
      (context.dependOnInheritedWidgetOfExactType<LoginBlocProvider>()
              as LoginBlocProvider)
          .loginBloc;
}
