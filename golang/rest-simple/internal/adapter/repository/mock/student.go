// Code generated by MockGen. DO NOT EDIT.
// Source: student.go

// Package mock_repository is a generated GoMock package.
package mock_repository

import (
	reflect "reflect"
	core "github.com/PatrickKoss/rest-simple/internal/core"

	uuid "github.com/google/uuid"
	gomock "go.uber.org/mock/gomock"
)

// MockStudentRepository is a mock of StudentRepository interface.
type MockStudentRepository struct {
	ctrl     *gomock.Controller
	recorder *MockStudentRepositoryMockRecorder
}

// MockStudentRepositoryMockRecorder is the mock recorder for MockStudentRepository.
type MockStudentRepositoryMockRecorder struct {
	mock *MockStudentRepository
}

// NewMockStudentRepository creates a new mock instance.
func NewMockStudentRepository(ctrl *gomock.Controller) *MockStudentRepository {
	mock := &MockStudentRepository{ctrl: ctrl}
	mock.recorder = &MockStudentRepositoryMockRecorder{mock}
	return mock
}

// EXPECT returns an object that allows the caller to indicate expected use.
func (m *MockStudentRepository) EXPECT() *MockStudentRepositoryMockRecorder {
	return m.recorder
}

// All mocks base method.
func (m *MockStudentRepository) All() ([]core.Student, error) {
	m.ctrl.T.Helper()
	ret := m.ctrl.Call(m, "All")
	ret0, _ := ret[0].([]core.Student)
	ret1, _ := ret[1].(error)
	return ret0, ret1
}

// All indicates an expected call of All.
func (mr *MockStudentRepositoryMockRecorder) All() *gomock.Call {
	mr.mock.ctrl.T.Helper()
	return mr.mock.ctrl.RecordCallWithMethodType(mr.mock, "All", reflect.TypeOf((*MockStudentRepository)(nil).All))
}

// Create mocks base method.
func (m *MockStudentRepository) Create(student core.CreateStudent) (core.Student, error) {
	m.ctrl.T.Helper()
	ret := m.ctrl.Call(m, "Create", student)
	ret0, _ := ret[0].(core.Student)
	ret1, _ := ret[1].(error)
	return ret0, ret1
}

// Create indicates an expected call of Create.
func (mr *MockStudentRepositoryMockRecorder) Create(student interface{}) *gomock.Call {
	mr.mock.ctrl.T.Helper()
	return mr.mock.ctrl.RecordCallWithMethodType(mr.mock, "Create", reflect.TypeOf((*MockStudentRepository)(nil).Create), student)
}

// Delete mocks base method.
func (m *MockStudentRepository) Delete(id uuid.UUID) error {
	m.ctrl.T.Helper()
	ret := m.ctrl.Call(m, "Delete", id)
	ret0, _ := ret[0].(error)
	return ret0
}

// Delete indicates an expected call of Delete.
func (mr *MockStudentRepositoryMockRecorder) Delete(id interface{}) *gomock.Call {
	mr.mock.ctrl.T.Helper()
	return mr.mock.ctrl.RecordCallWithMethodType(mr.mock, "Delete", reflect.TypeOf((*MockStudentRepository)(nil).Delete), id)
}

// Update mocks base method.
func (m *MockStudentRepository) Update(student core.UpdateStudent, id uuid.UUID) (core.Student, error) {
	m.ctrl.T.Helper()
	ret := m.ctrl.Call(m, "Update", student, id)
	ret0, _ := ret[0].(core.Student)
	ret1, _ := ret[1].(error)
	return ret0, ret1
}

// Update indicates an expected call of Update.
func (mr *MockStudentRepositoryMockRecorder) Update(student, id interface{}) *gomock.Call {
	mr.mock.ctrl.T.Helper()
	return mr.mock.ctrl.RecordCallWithMethodType(mr.mock, "Update", reflect.TypeOf((*MockStudentRepository)(nil).Update), student, id)
}
