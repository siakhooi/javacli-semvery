Name:           siakhooi-semvery
Version:        1.0.6
Release:        1%{?dist}
Summary:        semver utilities

License:        MIT
URL:            https://github.com/siakhooi/javacli-semvery
Source0:        https://github.com/siakhooi/%{name}/archive/refs/tags/${version}.tar.gz
BuildArch:      noarch

Requires:       bash
Requires:       jre-21-headless

%description
semver utilities

%prep

%install
mkdir -p %{buildroot}%{_bindir}
mkdir -p %{buildroot}%{_mandir}/man1
mkdir -p %{buildroot}%{_libdir}/java/siakhooi
install -m 0755 %{_working_directory}/usr/bin/* %{buildroot}%{_bindir}
install -m 644 %{_working_directory}/usr/share/man/man1/* %{buildroot}%{_mandir}/man1
install -m 644 %{_working_directory}/usr/lib/java/siakhooi/* %{buildroot}%{_libdir}/java/siakhooi
install %{_working_directory}/LICENSE %{_builddir}

%files
%license LICENSE
%{_bindir}/semvery
%{_mandir}/man1/semvery.1.gz
%{_libdir}/java/siakhooi/semvery*-jar-with-dependencies.jar

%changelog
* Fri Dec 12 2025 Siak Hooi <siakhooi@gmail.com> - 1.0.6
- Fix java bin

* Thu Dec 4 2025 Siak Hooi <siakhooi@gmail.com> - 1.0.5
- Fix Quality issues

* Wed Dec 3 2025 Siak Hooi <siakhooi@gmail.com> - 1.0.4
- Fix Quality issues

* Tue Dec 2 2025 Siak Hooi <siakhooi@gmail.com> - 1.0.3
- Upgrade to java 21

* Sun Nov 16 2025 Siak Hooi <siakhooi@gmail.com> - 1.0.2
- Dependencies upgrade

* Tue Oct 14 2025 Siak Hooi <siakhooi@gmail.com> - 1.0.1
- Dependencies upgrade

* Sat Dec 21 2024 Siak Hooi <siakhooi@gmail.com> - 1.0.0
- Release 1, apply formatter

* Tue Dec 17 2024 Siak Hooi <siakhooi@gmail.com> - 0.11.1
- fix rpm missing jre dependency

* Sun Dec 15 2024 Siak Hooi <siakhooi@gmail.com> - 0.11.0
- create rpm package

* Sun Dec 15 2024 Siak Hooi <siakhooi@gmail.com> - 0.10.3
- refactor workflows

* Sun Dec 15 2024 Siak Hooi <siakhooi@gmail.com> - 0.10.2
- add set-version.sh

* Fri Sep 08 2023 Siak Hooi <siakhooi@gmail.com> - 0.10.1
- rename 'invalid' to 'not valid'

* Thu Sep 07 2023 Siak Hooi <siakhooi@gmail.com> - 0.10.0
- add: isEquivalent

* Wed Sep 06 2023 Siak Hooi <siakhooi@gmail.com> - 0.9.0
- add: isEqual

* Tue Sep 05 2023 Siak Hooi <siakhooi@gmail.com> - 0.8.0
- add: isLower

* Tue Sep 05 2023 Siak Hooi <siakhooi@gmail.com> - 0.7.1
- fix isGreater description in man page

* Mon Sep 04 2023 Siak Hooi <siakhooi@gmail.com> - 0.7.0
- add isGreater

* Sat Sep 02 2023 Siak Hooi <siakhooi@gmail.com> - 0.6.0
- add option --any, exit code based on all or any results

* Sat Sep 02 2023 Siak Hooi <siakhooi@gmail.com> - 0.5.0
- tabular output

* Thu Aug 31 2023 Siak Hooi <siakhooi@gmail.com> - 0.4.0
- support multi values

* Sat Jun 24 2023 Siak Hooi <siakhooi@gmail.com> - 0.3.0
- add isStable

* Wed Jun 14 2023 Siak Hooi <siakhooi@gmail.com> - 0.2.0
- update help & man page, test refactoring

* Tue Jun 13 2023 Siak Hooi <siakhooi@gmail.com> - 0.1.0
- add: -o isValid

* Mon Jun 12 2023 Siak Hooi <siakhooi@gmail.com> - 0.0.2
- fix main class

* Mon Jun 12 2023 Siak Hooi <siakhooi@gmail.com> - 0.0.1
- first draft
